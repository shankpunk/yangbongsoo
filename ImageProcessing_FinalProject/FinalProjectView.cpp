
// FinalProjectView.cpp : CFinalProjectView Ŭ������ ����
//

#include "stdafx.h"
// SHARED_HANDLERS�� �̸� ����, ����� �׸� �� �˻� ���� ó���⸦ �����ϴ� ATL ������Ʈ���� ������ �� ������
// �ش� ������Ʈ�� ���� �ڵ带 �����ϵ��� �� �ݴϴ�.
#ifndef SHARED_HANDLERS
#include "FinalProject.h"
#endif

#include "FinalProjectDoc.h"
#include "FinalProjectView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CFinalProjectView

IMPLEMENT_DYNCREATE(CFinalProjectView, CView)

BEGIN_MESSAGE_MAP(CFinalProjectView, CView)
	// ǥ�� �μ� ����Դϴ�.
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CFinalProjectView::OnFilePrintPreview)
	ON_WM_CONTEXTMENU()
	ON_WM_RBUTTONUP()
	ON_COMMAND(IDM_HISTO_STRETCH, &CFinalProjectView::OnHistoStretch)
	ON_COMMAND(IDM_END_IN_SEARCH, &CFinalProjectView::OnEndInSearch)
	ON_COMMAND(IDM_HISTOGRAM, &CFinalProjectView::OnHistogram)
	ON_COMMAND(IDM_HISTO_EQUAL, &CFinalProjectView::OnHistoEqual)
	ON_COMMAND(IDM_EMBOSSING, &CFinalProjectView::OnEmbossing)
	ON_COMMAND(IDM_BLURR, &CFinalProjectView::OnBlurr)
	ON_COMMAND(IDM_SHARPENING, &CFinalProjectView::OnSharpening)
	ON_COMMAND(IDM_GAUSSIAN_FILTER, &CFinalProjectView::OnGaussianFilter)
	ON_COMMAND(IDM_HPF_SHARP, &CFinalProjectView::OnHpfSharp)
	ON_COMMAND(IDM_LPF_SHARP, &CFinalProjectView::OnLpfSharp)
	ON_COMMAND(IDM_DIFF_OPERATOR, &CFinalProjectView::OnDiffOperator)
	ON_COMMAND(IDM_HOMOGEN_OPERATOR, &CFinalProjectView::OnHomogenOperator)
	ON_COMMAND(IDM_CHA_OPERATOR, &CFinalProjectView::OnChaOperator)
	ON_COMMAND(IDM_LOG, &CFinalProjectView::OnLog)
	ON_COMMAND(IDM_DOG, &CFinalProjectView::OnDog)
	ON_COMMAND(IDM_ZOOM_IN, &CFinalProjectView::OnZoomIn)
	ON_COMMAND(IDM_NEAREST, &CFinalProjectView::OnNearest)
	ON_COMMAND(IDM_BILINEAR, &CFinalProjectView::OnBilinear)
	ON_COMMAND(IDM_ZOOM_OUT, &CFinalProjectView::OnZoomOut)
	ON_COMMAND(IDM_MEDIAN_SUB, &CFinalProjectView::OnMedianSub)
	ON_COMMAND(IDM_SUB, &CFinalProjectView::OnSub)
	ON_COMMAND(IDM_TRANSLATION, &CFinalProjectView::OnTranslation)
	ON_COMMAND(IDM_MIRROR_HOR, &CFinalProjectView::OnMirrorHor)
	ON_COMMAND(IDM_MIRROR_VER, &CFinalProjectView::OnMirrorVer)
	ON_COMMAND(IDM_ROTATION, &CFinalProjectView::OnRotation)
	ON_COMMAND(IDM_MEAN_FILTER, &CFinalProjectView::OnMeanFilter)
	ON_COMMAND(IDM_MEDIAN_FILTER, &CFinalProjectView::OnMedianFilter)
	ON_COMMAND(IDM_MAX_FILTER, &CFinalProjectView::OnMaxFilter)
	ON_COMMAND(IDM_MIN_FILTER, &CFinalProjectView::OnMinFilter)
	ON_COMMAND(IDM_ZOOM_IN_C, &CFinalProjectView::OnZoomInC)
END_MESSAGE_MAP()

// CFinalProjectView ����/�Ҹ�

CFinalProjectView::CFinalProjectView()
{
	// TODO: ���⿡ ���� �ڵ带 �߰��մϴ�.

}

CFinalProjectView::~CFinalProjectView()
{
}

BOOL CFinalProjectView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: CREATESTRUCT cs�� �����Ͽ� ���⿡��
	//  Window Ŭ���� �Ǵ� ��Ÿ���� �����մϴ�.

	return CView::PreCreateWindow(cs);
}

// CFinalProjectView �׸���

void CFinalProjectView::OnDraw(CDC* pDC)
{
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: ���⿡ ���� �����Ϳ� ���� �׸��� �ڵ带 �߰��մϴ�.
	int i,j;
	unsigned char R,G,B;
	for (i=0; i<pDoc->m_height; i++) {
		for (j=0; j<pDoc->m_width; j++) {
			R=G=B=pDoc->m_InputImage[i][j];
			pDC->SetPixel(j+5, i+5, RGB(R,G,B));
		}
	}

	for (i=0; i<pDoc->m_Re_height; i++) {
		for (j=0; j<pDoc->m_Re_width; j++) {
			R=G=B=pDoc->m_OutputImage[i][j];
			pDC->SetPixel(j+pDoc->m_width+10, i+5, RGB(R,G,B));
		}
	}
}


// CFinalProjectView �μ�


void CFinalProjectView::OnFilePrintPreview()
{
#ifndef SHARED_HANDLERS
	AFXPrintPreview(this);
#endif
}

BOOL CFinalProjectView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// �⺻���� �غ�
	return DoPreparePrinting(pInfo);
}

void CFinalProjectView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: �μ��ϱ� ���� �߰� �ʱ�ȭ �۾��� �߰��մϴ�.
}

void CFinalProjectView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: �μ� �� ���� �۾��� �߰��մϴ�.
}

void CFinalProjectView::OnRButtonUp(UINT /* nFlags */, CPoint point)
{
	ClientToScreen(&point);
	OnContextMenu(this, point);
}

void CFinalProjectView::OnContextMenu(CWnd* /* pWnd */, CPoint point)
{
#ifndef SHARED_HANDLERS
	theApp.GetContextMenuManager()->ShowPopupMenu(IDR_POPUP_EDIT, point.x, point.y, this, TRUE);
#endif
}


// CFinalProjectView ����

#ifdef _DEBUG
void CFinalProjectView::AssertValid() const
{
	CView::AssertValid();
}

void CFinalProjectView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CFinalProjectDoc* CFinalProjectView::GetDocument() const // ����׵��� ���� ������ �ζ������� �����˴ϴ�.
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CFinalProjectDoc)));
	return (CFinalProjectDoc*)m_pDocument;
}
#endif //_DEBUG


// CFinalProjectView �޽��� ó����


void CFinalProjectView::OnHistoStretch()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnHistoStretch();
	Invalidate(TRUE);

}


void CFinalProjectView::OnEndInSearch()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnEndInSearch();
	Invalidate(TRUE);
}


void CFinalProjectView::OnHistogram()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnHistogram();
	Invalidate(TRUE);
}


void CFinalProjectView::OnHistoEqual()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnHistoEqual();
	Invalidate(TRUE);
}


void CFinalProjectView::OnEmbossing()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnEmbossing();
	Invalidate(TRUE);
}


void CFinalProjectView::OnBlurr()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnBlurr();
	Invalidate(TRUE);
}


void CFinalProjectView::OnSharpening()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnSharpening();
	Invalidate(TRUE);
}


void CFinalProjectView::OnGaussianFilter()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnGaussianFilter();
	Invalidate(TRUE);
}


void CFinalProjectView::OnHpfSharp()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnHpfSharp();
	Invalidate(TRUE);
}


void CFinalProjectView::OnLpfSharp()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnLpfSharp();
	Invalidate(TRUE);
}


void CFinalProjectView::OnDiffOperator()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnDiffOperator();
	Invalidate(TRUE);
}


void CFinalProjectView::OnHomogenOperator()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnHomogenOperator();
	Invalidate(TRUE);
}


void CFinalProjectView::OnChaOperator()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnChaOperator();
	Invalidate(TRUE);
}


void CFinalProjectView::OnLog()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnLog();
	Invalidate(TRUE);
}


void CFinalProjectView::OnDog()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnDog();
	Invalidate(TRUE);
}


void CFinalProjectView::OnZoomIn()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnZoomIn();
	Invalidate(TRUE);
}


void CFinalProjectView::OnNearest()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnNearest();
	Invalidate(TRUE);
}


void CFinalProjectView::OnBilinear()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnBilinear();
	Invalidate(TRUE);
}


void CFinalProjectView::OnZoomOut()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnZoomOut();
	Invalidate(TRUE);
}


void CFinalProjectView::OnMedianSub()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMedianSub();
	Invalidate(TRUE);
}


void CFinalProjectView::OnSub()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnSub();
	Invalidate(TRUE);
}


void CFinalProjectView::OnTranslation()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnTranslation();
	Invalidate(TRUE);
}


void CFinalProjectView::OnMirrorHor()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMirrorHor();
	Invalidate(TRUE);

}


void CFinalProjectView::OnMirrorVer()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMirrorVer();
	Invalidate(TRUE);
}


void CFinalProjectView::OnRotation()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnRotation();
	Invalidate(TRUE);
}


void CFinalProjectView::OnMeanFilter()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMeanFilter();
	Invalidate(TRUE);
}


void CFinalProjectView::OnMedianFilter()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMedianFilter();
	Invalidate(TRUE);
}


void CFinalProjectView::OnMaxFilter()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMaxFilter();
	Invalidate(TRUE);
}


void CFinalProjectView::OnMinFilter()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMinFilter();
	Invalidate(TRUE);
}

void CFinalProjectView::OnZoomInC()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnZoomInC();
	Invalidate(TRUE);
}

