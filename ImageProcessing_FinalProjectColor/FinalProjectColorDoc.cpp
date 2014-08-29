
// FinalProjectColorDoc.cpp : CFinalProjectColorDoc 클래스의 구현
//

#include "stdafx.h"
// SHARED_HANDLERS는 미리 보기, 축소판 그림 및 검색 필터 처리기를 구현하는 ATL 프로젝트에서 정의할 수 있으며
// 해당 프로젝트와 문서 코드를 공유하도록 해 줍니다.
#ifndef SHARED_HANDLERS
#include "FinalProjectColor.h"
#endif

#include "FinalProjectColorDoc.h"

#include <propkey.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

// CFinalProjectColorDoc

IMPLEMENT_DYNCREATE(CFinalProjectColorDoc, CDocument)

BEGIN_MESSAGE_MAP(CFinalProjectColorDoc, CDocument)
	ON_COMMAND(IDM_ZOOM_IN, &CFinalProjectColorDoc::OnZoomIn)
	ON_COMMAND(IDM_NEAREST, &CFinalProjectColorDoc::OnNearest)
	ON_COMMAND(IDM_BILINEAR, &CFinalProjectColorDoc::OnBilinear)
	ON_COMMAND(IDM_ZOOM_OUT, &CFinalProjectColorDoc::OnZoomOut)
	ON_COMMAND(IDM_MEDIAN_SUB, &CFinalProjectColorDoc::OnMedianSub)
	ON_COMMAND(IDM_MEAN_SUB, &CFinalProjectColorDoc::OnMeanSub)
	ON_COMMAND(IDM_TRANSLATION, &CFinalProjectColorDoc::OnTranslation)
	ON_COMMAND(IDM_MIRROR_HOR, &CFinalProjectColorDoc::OnMirrorHor)
	ON_COMMAND(IDM_MIRROR_VER, &CFinalProjectColorDoc::OnMirrorVer)
	ON_COMMAND(IDM_ROTATION, &CFinalProjectColorDoc::OnRotation)
END_MESSAGE_MAP()


// CFinalProjectColorDoc 생성/소멸

CFinalProjectColorDoc::CFinalProjectColorDoc()
	: m_InputImageR(NULL)
	, m_InputImageG(NULL)
	, m_InputImageB(NULL)
	, m_OutputImageR(NULL)
	, m_OutputImageG(NULL)
	, m_OutputImageB(NULL)
	, m_width(0)
	, m_height(0)
	, m_Re_width(0)
	, m_Re_height(0)
{
	// TODO: 여기에 일회성 생성 코드를 추가합니다.

}

CFinalProjectColorDoc::~CFinalProjectColorDoc()
{
}

BOOL CFinalProjectColorDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: 여기에 재초기화 코드를 추가합니다.
	// SDI 문서는 이 문서를 다시 사용합니다.

	return TRUE;
}




// CFinalProjectColorDoc serialization

void CFinalProjectColorDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: 여기에 저장 코드를 추가합니다.
	}
	else
	{
		// TODO: 여기에 로딩 코드를 추가합니다.
	}
}

#ifdef SHARED_HANDLERS

// 축소판 그림을 지원합니다.
void CFinalProjectColorDoc::OnDrawThumbnail(CDC& dc, LPRECT lprcBounds)
{
	// 문서의 데이터를 그리려면 이 코드를 수정하십시오.
	dc.FillSolidRect(lprcBounds, RGB(255, 255, 255));

	CString strText = _T("TODO: implement thumbnail drawing here");
	LOGFONT lf;

	CFont* pDefaultGUIFont = CFont::FromHandle((HFONT) GetStockObject(DEFAULT_GUI_FONT));
	pDefaultGUIFont->GetLogFont(&lf);
	lf.lfHeight = 36;

	CFont fontDraw;
	fontDraw.CreateFontIndirect(&lf);

	CFont* pOldFont = dc.SelectObject(&fontDraw);
	dc.DrawText(strText, lprcBounds, DT_CENTER | DT_WORDBREAK);
	dc.SelectObject(pOldFont);
}

// 검색 처리기를 지원합니다.
void CFinalProjectColorDoc::InitializeSearchContent()
{
	CString strSearchContent;
	// 문서의 데이터에서 검색 콘텐츠를 설정합니다.
	// 콘텐츠 부분은 ";"로 구분되어야 합니다.

	// 예: strSearchContent = _T("point;rectangle;circle;ole object;");
	SetSearchContent(strSearchContent);
}

void CFinalProjectColorDoc::SetSearchContent(const CString& value)
{
	if (value.IsEmpty())
	{
		RemoveChunk(PKEY_Search_Contents.fmtid, PKEY_Search_Contents.pid);
	}
	else
	{
		CMFCFilterChunkValueImpl *pChunk = NULL;
		ATLTRY(pChunk = new CMFCFilterChunkValueImpl);
		if (pChunk != NULL)
		{
			pChunk->SetTextValue(PKEY_Search_Contents, value, CHUNK_TEXT);
			SetChunkValue(pChunk);
		}
	}
}

#endif // SHARED_HANDLERS

// CFinalProjectColorDoc 진단

#ifdef _DEBUG
void CFinalProjectColorDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CFinalProjectColorDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// CFinalProjectColorDoc 명령


BOOL CFinalProjectColorDoc::OnOpenDocument(LPCTSTR lpszPathName)
{
	if (!CDocument::OnOpenDocument(lpszPathName))
		return FALSE;

	// TODO:  여기에 특수화된 작성 코드를 추가합니다.
	// Color 이미지처리클래스
	CImage m_bmpBitmap;

	// 이미지파일로딩
	m_bmpBitmap.Load(lpszPathName); //칼라파일이 들어옴 

	// 높이와폭구하기
	m_height = m_bmpBitmap.GetHeight(); //getheight 를 통해서 영상의 크기를 알수 있음 
	m_width = m_bmpBitmap.GetWidth();

	// 메모리할당.
	m_InputImageR = new unsigned char* [m_height];
	for (int i=0; i<m_height; i++)
		m_InputImageR[i] = new unsigned char [m_width];

	m_InputImageG = new unsigned char* [m_height];
	for (int i=0; i<m_height; i++)
		m_InputImageG[i] = new unsigned char [m_width];

	m_InputImageB = new unsigned char* [m_height];
	for (int i=0; i<m_height; i++)
		m_InputImageB[i] = new unsigned char [m_width];


	// 칼라이미지를메모리에로딩
	COLORREF pixel;
	for (int i=0; i<m_height; i++)
		for(int j=0; j<m_width; j++) {
			pixel = m_bmpBitmap.GetPixel(i,j) ;

			m_InputImageR[j][i] = (unsigned char) GetRValue(pixel);
			m_InputImageG[j][i] = (unsigned char) GetGValue(pixel);
			m_InputImageB[j][i] = (unsigned char) GetBValue(pixel);
		}


		return TRUE;
}

void CFinalProjectColorDoc::OnCloseDocument()
{
	// TODO: 여기에 특수화된 코드를 추가 및/또는 기본 클래스를 호출합니다.
	int i;
	for (i=0; i<m_height; i++)
		delete m_InputImageR[i];
	delete m_InputImageR;

	for (i=0; i<m_height; i++)
		delete m_InputImageG[i];
	delete m_InputImageG;

	for (i=0; i<m_height; i++)
		delete m_InputImageB[i];
	delete m_InputImageB;

	if (m_OutputImageR != NULL) {
		for (i=0; i<m_Re_height; i++)
			delete m_OutputImageR[i];
		delete m_OutputImageR;
	}

	if (m_OutputImageG != NULL) {
		for (i=0; i<m_Re_height; i++)
			delete m_OutputImageG[i];
		delete m_OutputImageG;
	}

	if (m_OutputImageB != NULL) {
		for (i=0; i<m_Re_height; i++)
			delete m_OutputImageB[i];
		delete m_OutputImageB;
	}

	CDocument::OnCloseDocument();
}

#include "ConstantDlg.h"
void CFinalProjectColorDoc::OnZoomIn()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK){
	int scale =dlg.intValue;
	m_Re_height=m_height * scale;
	m_Re_width=m_width * scale;

	// 출력이미지메모리할당
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// 출력이미지영상처리<핵심알고리즘>
	int i,j;
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {
			m_OutputImageR[i*scale][j*scale] = m_InputImageR[i][j];
			m_OutputImageG[i*scale][j*scale] = m_InputImageG[i][j];
			m_OutputImageB[i*scale][j*scale] = m_InputImageB[i][j];
		}
	}
	}
}


void CFinalProjectColorDoc::OnNearest()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.intValue;

		m_Re_height = m_height * scale;
		m_Re_width = m_width * scale;

		// 메모리 할당
		m_OutputImageR = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageR[i] = new unsigned char [m_Re_width];

		m_OutputImageG = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageG[i] = new unsigned char [m_Re_width];

		m_OutputImageB = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageB[i] = new unsigned char [m_Re_width];


		// 역방향이니까 outputimage가 기준으로 
		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				m_OutputImageR[i][j] = m_InputImageR[i/scale][j/scale];
				m_OutputImageG[i][j] = m_InputImageG[i/scale][j/scale];
				m_OutputImageB[i][j] = m_InputImageB[i/scale][j/scale];
			}
		}
	}
	return;
}


void CFinalProjectColorDoc::OnBilinear()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.intValue;

		m_Re_height = m_height * scale;
		m_Re_width = m_width * scale;

		// 메모리 할당
		m_OutputImageR = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageR[i] = new unsigned char [m_Re_width];

		m_OutputImageG = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageG[i] = new unsigned char [m_Re_width];

		m_OutputImageB = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageB[i] = new unsigned char [m_Re_width];

		// 영상처리 알고리즘 핵심 
		double c1R,c2R,c3R,c4R; 
		double c1G,c2G,c3G,c4G; 
		double c1B,c2B,c3B,c4B; 
		double x,y; 
		double r_H, r_W; //실제 실수위치
		int i_H, i_W;  // 실제 정수위치 
		double N; // 실제 구하고 싶은 값 
		double N2, N3;

		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				r_H = i / (double)scale; 
				r_W = j / (double)scale;
				i_H = (int)r_H;
				i_W = (int)r_W;
				x = r_W - i_W;
				y = r_H - i_H;
				if( i_H < 0 || i_H >= (m_height-1) ||
					i_W < 0 || i_W >= (m_width-1) ){
						m_OutputImageR[i][j] = 255;
						m_OutputImageG[i][j] = 255;
						m_OutputImageB[i][j] = 255;
				}
				else{
					c1R = m_InputImageR[i_H][i_W];
					c2R = m_InputImageR[i_H][i_W+1];
					c3R = m_InputImageR[i_H+1][i_W+1];
					c4R = m_InputImageR[i_H+1][i_W];
					////////////////////////////////
					c1G = m_InputImageG[i_H][i_W];
					c2G = m_InputImageG[i_H][i_W+1];
					c3G = m_InputImageG[i_H+1][i_W+1];
					c4G = m_InputImageG[i_H+1][i_W];
					////////////////////////////////
					c1B = m_InputImageB[i_H][i_W];
					c2B = m_InputImageB[i_H][i_W+1];
					c3B = m_InputImageB[i_H+1][i_W+1];
					c4B = m_InputImageB[i_H+1][i_W];
					////////////////////////////////
					N = c1R*(1-x)*(1-y) + c2R*x*(1-y) + c3R*x*y + c4R*(1-x)*y ;
					N2 = c1G*(1-x)*(1-y) + c2G*x*(1-y) + c3G*x*y + c4G*(1-x)*y ;
					N3 = c1B*(1-x)*(1-y) + c2B*x*(1-y) + c3B*x*y + c4B*(1-x)*y ;

					m_OutputImageR[i][j] = (unsigned char) N;
					m_OutputImageG[i][j] = (unsigned char) N2;
					m_OutputImageB[i][j] = (unsigned char) N3;
				}
			}
		}
	}
	return;
}


void CFinalProjectColorDoc::OnZoomOut()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {
	int scale =dlg.intValue;

	m_Re_height= m_height / scale;
	m_Re_width = m_width / scale;

	// 출력이미지메모리할당
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// 출력이미지영상처리<핵심알고리즘>
	int i,j;
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {

			m_OutputImageR[i/scale][j/scale] = m_InputImageR[i][j];
			m_OutputImageG[i/scale][j/scale] = m_InputImageG[i][j];
			m_OutputImageB[i/scale][j/scale] = m_InputImageB[i][j];
		}
	}
	}
}


void CFinalProjectColorDoc::OnMedianSub()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.intValue;

		m_Re_height = m_height / scale;
		m_Re_width = m_width / scale;

		// 메모리 할당
		m_OutputImageR = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageR[i] = new unsigned char [m_Re_width];

		m_OutputImageG = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageG[i] = new unsigned char [m_Re_width];

		m_OutputImageB = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageB[i] = new unsigned char [m_Re_width];


		int mSize = scale; 
		double **Mask;
		double **Mask2;
		double **Mask3;

		Mask = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask[i] = new double[mSize];

		Mask2 = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask2[i] = new double[mSize];

		Mask3 = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask3[i] = new double[mSize];

		int n,m; 

		for(i=0; i<m_height - (mSize/2); i+=mSize) {
			for(j=0; j<m_width - (mSize/2) ; j+=mSize) {
				// 마스크 채우기 	
				for(n=0;n<mSize;n++){
					for(m=0;m<mSize;m++){
						Mask[n][m] = m_InputImageR[i+n][j+m]; 
						Mask2[n][m] = m_InputImageG[i+n][j+m]; 
						Mask3[n][m] = m_InputImageB[i+n][j+m]; 
					}
				}		

				m_OutputImageR[i/mSize][j/mSize] = OnFindMedian(Mask, mSize);
				m_OutputImageG[i/mSize][j/mSize] = OnFindMedian(Mask2, mSize);
				m_OutputImageB[i/mSize][j/mSize] = OnFindMedian(Mask3, mSize);
			}
		}
	}
	return;
}


unsigned char CFinalProjectColorDoc::OnFindMedian(double** Mask, int mSize)
{
	int i,j, index=0;
	double *Mask1;
	Mask1 = new double[mSize * mSize]; 

	//2차원을 -> 1차원으로 
	for(i=0;i<mSize;i++){
		for(j=0;j<mSize;j++){
			Mask1[index++] = Mask[i][j];
		}
	}

	//셀렉트정렬 
	for(i=0; i<mSize * mSize; i++){
		for(j=0; j<mSize * mSize -1 ; j++){
			if(Mask1[j] > Mask1[j+1]){
				double temp = Mask1[j];
				Mask1[j] = Mask1[j+1];
				Mask1[j+1] = temp; 
			}
		}
	}

	return (unsigned char)Mask1[mSize * mSize / 2];
}


void CFinalProjectColorDoc::OnMeanSub()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.intValue;

		m_Re_height = m_height / scale;
		m_Re_width = m_width / scale;

		// 메모리 할당
		m_OutputImageR = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageR[i] = new unsigned char [m_Re_width];

		m_OutputImageG = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageG[i] = new unsigned char [m_Re_width];

		m_OutputImageB = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageB[i] = new unsigned char [m_Re_width];


		int mSize = scale; 
		double **Mask;
		double **Mask2;
		double **Mask3;

		Mask = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask[i] = new double[mSize];

		Mask2 = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask2[i] = new double[mSize];

		Mask3 = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask3[i] = new double[mSize];

		int n,m; 

		for(i=0; i<m_height - (mSize/2); i+=mSize) {
			for(j=0; j<m_width - (mSize/2) ; j+=mSize) {
				// 마스크 채우기 	
				for(n=0;n<mSize;n++){
					for(m=0;m<mSize;m++){
						Mask[n][m] = m_InputImageR[i+n][j+m]; 
						Mask2[n][m] = m_InputImageG[i+n][j+m];
						Mask3[n][m] = m_InputImageB[i+n][j+m];
					}
				}		
				m_OutputImageR[i/mSize][j/mSize] = OnFindAVG(Mask, mSize);
				m_OutputImageG[i/mSize][j/mSize] = OnFindAVG(Mask2, mSize);
				m_OutputImageB[i/mSize][j/mSize] = OnFindAVG(Mask3, mSize);
			}
		}
	}
	return;

}


unsigned char CFinalProjectColorDoc::OnFindAVG(double** Mask, int mSize)
{
	int i,j;
	double sum = 0.0, max = 0.0;
	//double *Mask1;
	//Mask1 = new double[mSize * mSize]; 

	for(i=0;i<mSize;i++){
		for(j=0;j<mSize;j++){
			sum += Mask[i][j] ;  
		}
	}

	max = sum / (mSize *mSize) ;

	return max;
}

#include "ConstantDlg2.h"
void CFinalProjectColorDoc::OnTranslation()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CConstantDlg2 dlg;
	if(dlg.DoModal() == IDOK) {
	m_Re_height= m_height;
	m_Re_width = m_width;
	
	int dx = dlg.yValue;
	int dy= dlg.xValue; 

	// 출력이미지메모리할당
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// 출력이미지영상처리<핵심알고리즘>
	int i,j;
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(i+dx >= 0 && i+dx < m_Re_height && j+dy >= 0 && j+dy < m_Re_width){
				m_OutputImageR[i+dx][j+dy] = m_InputImageR[i][j];
				m_OutputImageG[i+dx][j+dy] = m_InputImageG[i][j];
				m_OutputImageB[i+dx][j+dy] = m_InputImageB[i][j];
			}
		}
	}
	}
}


void CFinalProjectColorDoc::OnMirrorHor()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height= m_height;
	m_Re_width = m_width;


	// 출력이미지메모리할당
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// 출력이미지영상처리<핵심알고리즘>
	int i,j;
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {

			m_OutputImageR[i][m_height-j-1] = m_InputImageR[i][j];
			m_OutputImageG[i][m_height-j-1] = m_InputImageG[i][j];
			m_OutputImageB[i][m_height-j-1] = m_InputImageB[i][j];

		}
	}
}


void CFinalProjectColorDoc::OnMirrorVer()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height= m_height;
	m_Re_width = m_width;


	// 출력이미지메모리할당
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// 출력이미지영상처리<핵심알고리즘>
	int i,j;
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {

			m_OutputImageR[m_height-i-1][j] = m_InputImageR[i][j];
			m_OutputImageG[m_height-i-1][j] = m_InputImageG[i][j];
			m_OutputImageB[m_height-i-1][j] = m_InputImageB[i][j];

		}
	}
}

#include <math.h>
void CFinalProjectColorDoc::OnRotation()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK){
	int angle = dlg.intValue; //각도 입력
	double r = angle * 3.142592 / 180.0 ; //각도를 라디안 값으로 변환 
	double r90 = (90-angle) * 3.142592 / 180.0 ;
	int i,j;

	m_Re_height = m_height * cos(r) + m_width * cos(r90);
	m_Re_width = m_height * cos(r90) + m_width * cos(r);

	int x,y, xd, yd; 
	int cx = m_Re_height /2 , cy = m_Re_height /2; //기준점 설정

	// 출력이미지메모리할당
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	for(i=0; i<m_Re_height; i++){
		for(j=0; j<m_Re_width; j++){ 
			m_OutputImageR[i][j] = 255;
			m_OutputImageG[i][j] = 255;
			m_OutputImageB[i][j] = 255;
		}
	}
	//임시 입력 이미지 
	double **m_TempImage1;
	double **m_TempImage2;
	double **m_TempImage3;
	m_TempImage1 = new double*[m_Re_height];
	for(i=0; i<m_Re_height;i++)
		m_TempImage1[i] = new double[m_Re_width];

	for(i=0; i<m_Re_height; i++)
		for(j=0; j<m_Re_width; j++) 
			m_TempImage1[i][j] = 255;

	m_TempImage2 = new double*[m_Re_height];
	for(i=0; i<m_Re_height;i++)
		m_TempImage2[i] = new double[m_Re_width];

	for(i=0; i<m_Re_height; i++)
		for(j=0; j<m_Re_width; j++) 
			m_TempImage2[i][j] = 255;

	m_TempImage3 = new double*[m_Re_height];
	for(i=0; i<m_Re_height;i++)
		m_TempImage3[i] = new double[m_Re_width];

	for(i=0; i<m_Re_height; i++)
		for(j=0; j<m_Re_width; j++) 
			m_TempImage3[i][j] = 255;

	//원 영상 -> 임시 
	int dx = (m_Re_height - m_height) /2;
	int dy = (m_Re_width - m_width) /2;

	for(i=0; i<m_height; i++){
		for(j=0; j<m_width; j++){
			m_TempImage1[i+dx][j+dy] = m_InputImageR[i][j];
		} 
	}

	for(i=0; i<m_height; i++){
		for(j=0; j<m_width; j++){
			m_TempImage2[i+dx][j+dy] = m_InputImageG[i][j];
		} 
	}

	for(i=0; i<m_height; i++){
		for(j=0; j<m_width; j++){
			m_TempImage3[i+dx][j+dy] = m_InputImageB[i][j];
		} 
	}

	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {

			x=i;
			y=j;
			xd = ((x-cx)*cos(r) + (y-cy)*sin(r)) +cx ; // 다 끝나고 다시 cx cy 원상값으로 설정 
			yd = (- (x-cx)*sin(r) + (y-cy)*cos(r)) +cy;

			if(xd >= 0 && xd < m_Re_height && yd >= 0 && yd < m_Re_width){
				m_OutputImageR[x][y] = m_TempImage1[xd][yd];
				m_OutputImageG[x][y] = m_TempImage2[xd][yd];
				m_OutputImageB[x][y] = m_TempImage3[xd][yd];
			}
		}
	}
	}
}
